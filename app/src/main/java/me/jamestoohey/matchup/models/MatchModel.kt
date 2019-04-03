package me.jamestoohey.matchup.models

import me.jamestoohey.matchup.data.entity.Team

class MatchModel(
    var toid: Long,
    var matchId: Long,
    var matchName: String,
    var homeTeam: Team?,
    var awayTeam: Team?,
    var homeMatch: MatchModel?,
    var awayMatch: MatchModel?,
    var htScore: Long?,
    var atScore: Long?
    )