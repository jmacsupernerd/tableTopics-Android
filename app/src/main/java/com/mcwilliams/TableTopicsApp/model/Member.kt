package com.mcwilliams.TableTopicsApp.model

import kotlin.properties.Delegates

/**
 * Created by m439047 on 5/29/13.
 */
class Member {

    var _id: Int = 0
    var _name: String by Delegates.notNull()

    constructor() {
    }

    constructor(name: String) {
        this._name = name
    }

    constructor(id: Int, name: String) {
        this._name = name
        this._id = id
    }
}
