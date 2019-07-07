package saeed.spo.challenge.scheduling.common.model

import com.fasterxml.jackson.annotation.JsonInclude
import saeed.spo.challenge.scheduling.common.application.dto.OptimiserResponse

data class ApiResponse(val error: Boolean, val message: String, @JsonInclude(JsonInclude.Include.NON_NULL) val results: List<OptimiserResponse>? = null)