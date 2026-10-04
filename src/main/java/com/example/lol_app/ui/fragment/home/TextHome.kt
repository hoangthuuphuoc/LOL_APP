package com.example.lol_app.ui.fragment.home

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Size
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.lol_app.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val x1 = 100f
    private val y1 = 1500f
    private val x2 = 100f
    private val y2 = 500f
    private val y3 = 1500f
var textt="alo1"
    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL_AND_STROKE
        textSize = 100f
        strokeWidth = 10f
    }
    private val linePaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.blue)
        style = Paint.Style.FILL
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        textSize = 30f
        textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        drawGrid(canvas)
//        drawFill(canvas)
//        drawLine(canvas)
        veText(canvas)
//        tron(canvas)
//vePin(canvas,300f,height/2.toFloat(),3,R.color.blue)
//        veNgoiSao(canvas)

    }

    fun setText(text: String){
        textt=text
        invalidate()
    }

    fun veText(canvas: Canvas){
        canvas.drawText(
            textt,
            width/2.toFloat(),
            height/2.toFloat(),
            textPaint
        )
    }

    fun setColor(color: Int){
        textPaint.color= color
        invalidate()
    }
    fun setFont(fontId: Int){
        textPaint.typeface= ResourcesCompat.getFont(
            context,
            fontId
        )
        invalidate()
    }
    fun setSize(size: Float){
        textPaint.textSize=size
        invalidate()
    }


    fun drawLine(canvas: Canvas) {
        val x3 = width - 100f
        canvas.drawLine(x1, y1, x2, y2, borderPaint)
        canvas.drawLine(x1, y1, x3, y3, borderPaint)
    }

    fun drawFill(canvas: Canvas) {
        val a = 100f
        val padding = 200f
        val height1 = 1000f
        val left = 100f
        val width = 100f
        val right = left + width
        val bottom1 = 1500f
        val top = bottom1 - height1
        val numberChart = 4
        for (i in 0..numberChart) {
            canvas.drawRect(
                left + (width + padding) * i,
                top - (a * i),
                right + (width + padding) * i,
                bottom1,
                linePaint
            )
            canvas.drawText(
                "Q" + i,
                (left + (width + padding) * i + right + (width + padding) * i) / 2,
                bottom1 + 100f,
                textPaint
            )
        }


    }

    private fun drawGrid(canvas: Canvas) {
        val x3 = width - 100f
        val chartHeight = y1 - y2
        val maxValue = 1500
        val numberOfSteps = 10

        for (i in 0..numberOfSteps) {

            val y = y1 - chartHeight * i / numberOfSteps

            val value = maxValue * i / numberOfSteps
            val name = "Q" + " " + value


            if (i != 0) {
                canvas.drawLine(
                    x1, y, x3, y, borderPaint
                )
            }
            canvas.drawText(
                name, x1 - 10f, y + 10f, textPaint
            )

        }

    }

    private fun tron(canvas: Canvas) {

        val danhSachGiaTri = floatArrayOf(
            10f, 35f, 19f, 14f, 22f
        )

        val danhSachMau = intArrayOf(
            ContextCompat.getColor(context, R.color.red),
            ContextCompat.getColor(context, R.color.blue),
            ContextCompat.getColor(context, R.color.gray_light),
            ContextCompat.getColor(context, R.color.poplar),
            ContextCompat.getColor(context, R.color.black_2)
        )


        val butVe = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.FILL
        }


        val tamX = width / 2f
        val tamY = height / 2f
        val banKinh = 400f

        val khungHinhTron = RectF(
            tamX - banKinh, tamY - banKinh, tamX + banKinh, tamY + banKinh
        )


        var gocBatDau = -90f


        for (i in danhSachGiaTri.indices) {


            val gocQuet = danhSachGiaTri[i] / 100f * 360f

            butVe.color = danhSachMau[i]

            canvas.drawArc(
                khungHinhTron, gocBatDau, gocQuet, true, butVe
            )

            gocBatDau += gocQuet
        }
    }

    private fun vePin(
        canvas: Canvas, x: Float, y: Float, soVachPin: Int, mauPin: Int
    ) {

        val chieuRongThanPin = 450f
        val chieuCaoThanPin = 200f
        val chieuRongDauPin = 50f

        val traiThanPin = x + chieuRongDauPin
        val trenThanPin = y
        val phaiThanPin = traiThanPin + chieuRongThanPin
        val duoiThanPin = y + chieuCaoThanPin

        val butVien = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 10f
        }

        val butMau = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, mauPin)
            style = Paint.Style.FILL
        }

        canvas.drawRect(
            x, y + 60f, traiThanPin, y + 140f, butVien
        )

        canvas.drawRect(
            traiThanPin, trenThanPin, phaiThanPin, duoiThanPin, butVien
        )

        val tongSoVach = 5

        val leTrong = 20f

        val khoangCachVach = 10f

        val chieuRongBenTrong = chieuRongThanPin - leTrong * 2

        val chieuRongMoiVach = (chieuRongBenTrong - khoangCachVach * (tongSoVach - 1)) / tongSoVach
        for (i in 0 until soVachPin) {

            val benPhaiVach = phaiThanPin - leTrong - i * (chieuRongMoiVach + khoangCachVach)

            val benTraiVach = benPhaiVach - chieuRongMoiVach

            canvas.drawRect(
                benTraiVach, trenThanPin + leTrong, benPhaiVach, duoiThanPin - leTrong, butMau
            )
        }
    }

    private fun veNgoiSao(canvas: Canvas) {

        val tamX = width / 2f
        val tamY = height / 2f

        val banKinhNgoai = 550f
        val banKinhTrong = 300f

        val duongNgoiSao = Path()

        val butVe = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = ContextCompat.getColor(context, R.color.red)
            style = Paint.Style.STROKE
            strokeWidth = 15f
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }

        for (i in 0 until 10) {


            val banKinh = if (i % 2 == 0) {
                banKinhNgoai
            } else {
                banKinhTrong
            }

            val gocDo = i * 36f - 90f
            val gocRadian = Math.toRadians(gocDo.toDouble())

            val diemX = tamX + banKinh * kotlin.math.cos(gocRadian).toFloat()

            val diemY = tamY + banKinh * kotlin.math.sin(gocRadian).toFloat()

            if (i == 0) {
                duongNgoiSao.moveTo(diemX, diemY)
            } else {
                duongNgoiSao.lineTo(diemX, diemY)
            }
        }
        duongNgoiSao.close()
        canvas.drawPath(duongNgoiSao, butVe)
    }
}