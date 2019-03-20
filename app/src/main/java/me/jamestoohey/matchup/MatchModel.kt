package me.jamestoohey.matchup

class MatchModel(
    var toid: Long,
    var matchName: String,
    var htName: String?,
    var htImage: String?,
    var htScore: Long?,
    var atName: String?,
    var atImage: String?,
    var atScore: Long?
)

//[matchName,htName,htImage,htScore,atName,atImage,atScore]
//[toid,matchId,name,image_path,home_goals,name,image_path,away_goals]