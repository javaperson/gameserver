package com.github.javaperson.gameserver.anno

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class HeHandler(val id: Int = 0)


