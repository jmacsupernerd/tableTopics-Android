package com.mcwilliams.TableTopicsApp.model

import kotlin.properties.Delegates

/**
 * Created by m439047 on 5/29/13.
 */
class Topic {

    var _id: Int = 0
    var _topic: String by Delegates.notNull()

    constructor() {
    }

    constructor(topic: String) {
        this._topic = topic
    }

    constructor(id: Int, topic: String) {
        this._topic = topic
        this._id = id
    }
}
