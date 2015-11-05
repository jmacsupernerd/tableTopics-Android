package com.mcwilliams.TableTopicsApp.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.mcwilliams.TableTopicsApp.FragmentAdapter
import com.mcwilliams.TableTopicsApp.R
import com.mcwilliams.TableTopicsApp.TableTopicsApplication
import com.mcwilliams.TableTopicsApp.arrayadapters.PeopleRVAdapter
import com.mcwilliams.TableTopicsApp.arrayadapters.TopicsRvAdapter
import com.mcwilliams.TableTopicsApp.databinding.DialogImportTopicsBinding
import com.mcwilliams.TableTopicsApp.databinding.LvRowCategoryBinding
import com.mcwilliams.TableTopicsApp.fragments.HomeFragment
import com.mcwilliams.TableTopicsApp.fragments.People
import com.mcwilliams.TableTopicsApp.fragments.Topics
import com.mcwilliams.TableTopicsApp.model.Member
import com.mcwilliams.TableTopicsApp.model.Topic
import com.mcwilliams.TableTopicsApp.model.response.Categories
import com.mcwilliams.TableTopicsApp.model.response.TopicsByCategory
import com.mcwilliams.TableTopicsApp.utils.network.TopicServices

import java.util.ArrayList

import butterknife.Bind
import butterknife.OnClick
import butterknife.bindView

import retrofit.Call
import retrofit.Callback
import retrofit.Response
import kotlin.properties.Delegates

/**
 * Created by joshuamcwilliams on 7/2/15.
 */
class Main : AppCompatActivity(), ViewPager.OnPageChangeListener {

    val toolbar: Toolbar by bindView(R.id.toolbar)
    val viewPager: ViewPager by bindView(R.id.viewpager)
    val fab: FloatingActionButton by bindView(R.id.fab)
    val tabLayout: TabLayout by bindView(R.id.tabs)
    val adView: AdView by bindView(R.id.adView)

    var topicServices: TopicServices by Delegates.notNull()
    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)
        topicServices = TableTopicsApplication.retrofit.create(TopicServices::class.java)
        setSupportActionBar(toolbar)
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)
        fab.visibility = View.GONE
        fab.setOnClickListener{
            fabClicked()
        }

        val adRequest = AdRequest.Builder().addTestDevice("6D12C149D56231562E110E3C73BAC46A").build()
        adView.loadAd(adRequest)

        viewPager.addOnPageChangeListener(this)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = FragmentAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment(), "Home")
        adapter.addFragment(Topics(), "Topics")
        adapter.addFragment(People(), "People")
        viewPager.adapter = adapter
    }

    override fun onPageScrolled(i: Int, v: Float, i1: Int) {

    }

    override fun onPageSelected(i: Int) {
        if (i == 0) {
            fab.visibility = View.GONE
            //            adView.setVisibility(View.GONE);
        } else {
            fab.visibility = View.VISIBLE
            //            adView.setVisibility(View.VISIBLE);
        }
    }

    override fun onPageScrollStateChanged(i: Int) {

    }

    fun fabClicked() {
        showDialog(viewPager.adapter.getPageTitle(viewPager.currentItem))
    }

    fun showDialog(pageTitle: CharSequence) {
        Log.d("", pageTitle as String)
        val alert = AlertDialog.Builder(this)

        if (pageTitle == "Home") {

        } else if (pageTitle == "Topics") {
            alert.setTitle("Add Topic")
            val inputView = layoutInflater.inflate(R.layout.input_dialog, null)
            val getInput = inputView.findViewById(R.id.inputText) as EditText
            getInput.requestFocus()
            alert.setView(inputView)
            alert.setPositiveButton("Add", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    TableTopicsApplication.db.addTopic(Topic(getInput.text.toString()))
                    Topics.reloadData()
                }
            })
            alert.setNeutralButton("Predefined Topics", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    val categoriesCall = topicServices.categories
                    categoriesCall.enqueue(object : Callback<Categories> {
                        override fun onResponse(response: Response<Categories>) {
                            Log.d("", response.body().results.size.toString())
                            showPredefinedTopicDialog(response.body().results)
                            dialog.dismiss()
                        }

                        override fun onFailure(t: Throwable) {
                            Log.d("", "Error")
                        }
                    })
                }
            })
            alert.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                }
            })
        } else {
            alert.setTitle("Add Person")
            val inputView = layoutInflater.inflate(R.layout.input_dialog, null)
            val getInput = inputView.findViewById(R.id.inputText) as EditText
            getInput.requestFocus()
            alert.setView(inputView)
            alert.setPositiveButton("Add", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    TableTopicsApplication.db.addMember(Member(getInput.text.toString()))
//                    People.reloadData()
                }
            })
            alert.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                }
            })
        }
        alert.show()
    }

    fun showPredefinedTopicDialog(categoriesList: List<Categories.Category>) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Select a category")

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)

        for (categories in categoriesList) {
            val binding = DataBindingUtil.inflate<LvRowCategoryBinding>(layoutInflater, R.layout.lv_row_category, null, false)
            binding.tvCategory.text = categories.categoryName
            binding.tvCategory.tag = categories.categoryName
            binding.tvCategory.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    loadTopicsFromCategories(v)
                }
            })
            linearLayout.addView(binding.root)
        }
        linearLayout.layoutParams = params

        alert.setView(linearLayout)
        dialog = alert.create()
        dialog!!.show()
    }

    fun loadTopicsFromCategories(v: View) {
        val getTopics = topicServices.getTopicsByCategory(v.tag as String)
        getTopics.enqueue(object : Callback<TopicsByCategory> {
            override fun onResponse(response: Response<TopicsByCategory>) {
                dialog!!.dismiss()
                onCategoryClicked(response.body().results)
            }

            override fun onFailure(t: Throwable) {

            }
        })
    }

    fun onCategoryClicked(topics: List<TopicsByCategory.Topic>) {
        val topicList = ArrayList<Topic>()

        for (topic in topics) {
            val newTopic = Topic(topic.topic)
            topicList.add(newTopic)
        }

        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        val dialogBinding = DataBindingUtil.inflate<DialogImportTopicsBinding>(layoutInflater, R.layout.dialog_import_topics, null, false)

        dialogBinding.tbToolbar.setNavigationIcon(R.drawable.ic_clear_white_24dp)
        dialogBinding.tbToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                dialog.dismiss()
            }
        })
        dialogBinding.tbToolbar.inflateMenu(R.menu.menu)
        dialogBinding.tbToolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                for (topic in topicList) {
                    TableTopicsApplication.db.addTopic(Topic(topic._topic))
                }
                Topics.reloadData()
                dialog.dismiss()
                return true
            }
        })

        dialogBinding.rvTopics.setHasFixedSize(true)
        dialogBinding.rvTopics.layoutManager = LinearLayoutManager(this)
        dialogBinding.rvTopics.adapter = TopicsRvAdapter(topicList)

        dialog.setContentView(dialogBinding.root)
        dialog.window.attributes.windowAnimations = R.style.DialogAnimation
        dialog.show()

    }
}
