package me.jamestoohey.matchup.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import me.jamestoohey.matchup.R


/**
 * Keeping this class as a reference for further development - this is the draw view I was using to generate a
 * view of the tournament bracket. I intend to add to this when I've got more time.
 */
class DrawView(context: Context, attributeSet: AttributeSet?): View(context, attributeSet) {
    private val linePaint = Paint()
    private val defaultBarWidth = resources.getDimensionPixelSize(R.dimen.line_default_width)
    private val defaultBarHeight = resources.getDimensionPixelSize(R.dimen.line_default_height)

    init {
        linePaint.color = Color.GREEN
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawMatch(canvas)
    }

    private fun drawMatch(canvas: Canvas) {
        val teamHeight = 40.0F
        val teamWidth = 120.0F

        val distanceBetweenTeams = 40.0F

        val teamStartWidth = 20.0F
        val firstTeamStartHeight = 20.0F
        val secondTeamStartHeight = firstTeamStartHeight + teamHeight +  distanceBetweenTeams

        canvas.drawRect(teamStartWidth, firstTeamStartHeight, teamWidth + teamStartWidth, firstTeamStartHeight + teamHeight, linePaint)

        canvas.drawRect(teamStartWidth, secondTeamStartHeight, teamWidth + teamStartWidth, secondTeamStartHeight + teamHeight, linePaint)

        val firstTeamConY = firstTeamStartHeight + (teamHeight / 2)
        val secondTeamConY = secondTeamStartHeight + (teamHeight / 2)

        canvas.drawLine(teamWidth + teamStartWidth, firstTeamConY, teamWidth + teamStartWidth + 100.0F, firstTeamConY, linePaint)

        canvas.drawLine(teamWidth + teamStartWidth, secondTeamConY, teamWidth + teamStartWidth + 100.0F, secondTeamConY, linePaint)

        canvas.drawLine(teamWidth + teamStartWidth + 100.0F, firstTeamConY, teamWidth + teamStartWidth + 100.0F, secondTeamConY, linePaint)
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> defaultBarWidth
            View.MeasureSpec.UNSPECIFIED -> defaultBarWidth
            else -> defaultBarWidth
        }

        val height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> defaultBarHeight
            View.MeasureSpec.UNSPECIFIED -> defaultBarHeight
            else -> defaultBarHeight
        }

        setMeasuredDimension(width, height)
    }
}