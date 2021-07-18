package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.Cage

data class CageServerResponse(override var respError: String?, var cages: List<Cage>): ServerResponse(respError)