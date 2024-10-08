package com.davega.domain.shared.base

import kotlinx.coroutines.flow.Flow

interface FlowUseCase {

    interface ParamsAndResult<Parameters, Result>: FlowUseCase {
        suspend operator fun invoke(params: Parameters): Flow<Result>
    }

    interface OptionalParamsAndResult<Parameters, Result>: FlowUseCase {
        suspend operator fun invoke(params: Parameters? = null): Flow<Result>
    }

    interface OnlyResult<Result>: FlowUseCase {
        suspend operator fun invoke(): Flow<Result>
    }

}