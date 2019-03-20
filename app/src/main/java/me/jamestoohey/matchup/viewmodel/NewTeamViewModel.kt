package me.jamestoohey.matchup.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import me.jamestoohey.matchup.data.entity.Team
import me.jamestoohey.matchup.repository.TeamRepository

class NewTeamViewModel(application: Application): AndroidViewModel(application) {
    private val teamRepository: TeamRepository = TeamRepository(application)

    fun insert(team: Team) {
        teamRepository.insert(team)
    }

}