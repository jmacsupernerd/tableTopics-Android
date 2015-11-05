package com.mcwilliams.TableTopicsApp.model

import kotlin.properties.Delegates

/**
 * Created by m439047 on 5/29/13.
 */
class Category {

    var _id: Int = 0
    var _category: String by Delegates.notNull()

    constructor() {
    }

    constructor(category: String) {
        this._category = category
    }

    constructor(id: Int, category: String) {
        this._category = category
        this._id = id
    }
}
