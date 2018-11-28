package com.github.javaperson.gameserver.anno

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class HeService(val name: String)
