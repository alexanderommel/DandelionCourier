package com.tongue.dandelioncourier.domain

sealed interface BusinessRuleBreach {
    object ServiceUnavailable: BusinessRuleBreach
}