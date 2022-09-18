package com.tongue.dandelioncourier.domain

sealed class UseCaseResult<T, E: BusinessRuleBreach> {
    class Success<T: Any,E : BusinessRuleBreach>(val payload: T): UseCaseResult<T,E>()
    class Error<T: Any,E: BusinessRuleBreach>(val breach: E): UseCaseResult<T,E>()
}