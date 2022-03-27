package com.chumak.testapp.data.mappers

interface BaseMapper<Bean, Entity> {
    fun mapFromBean(src: Bean): Entity
}
