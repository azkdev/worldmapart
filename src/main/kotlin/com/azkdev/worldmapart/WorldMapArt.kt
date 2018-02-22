package com.azkdev.worldmapart

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import processing.core.PApplet
import java.io.FileInputStream

class WorldMapArt: PApplet() {

    private lateinit var mapper: ObjectMapper
    private lateinit var cities: ArrayList<DataCity>
    private lateinit var drawableCities: ArrayList<City>
    private lateinit var countries: ArrayList<String>
    private lateinit var colors: ArrayList<Int>

    override fun settings() {
        size(Constants.CW.toInt(), Constants.CH.toInt())
    }

    override fun setup() {
        background(Constants.BG)
        mapper = jacksonObjectMapper()
        cities = mapper.readValue(FileInputStream(this.javaClass.getResource("/data/city.list.json").file))
        drawableCities = arrayListOf()
        countries = arrayListOf()
        colors = arrayListOf()
        cities.forEach {
            if (!countries.contains(it.country)) {
                countries.add(it.country)
                colors.add(color(random(255F), random(255F), random(255F)))
            }
            val city = City(it, colors[countries.indexOf(it.country)])
            drawableCities.add(city)
            city.draw()
        }
    }

    override fun draw() {

    }

    inner class City {

        private val id: Int
        private val name: String
        private val country: String
        private val coord: Coord
        private val color: Int

        var r: Float = 1F

        constructor(dataCity: DataCity, color: Int) {
            id = dataCity.id
            name = dataCity.name
            country = dataCity.country
            coord = dataCity.coord
            this.color = color
        }

        fun draw() {
            noStroke()
            fill(color)
            ellipse((coord.lon * 4 + Constants.CW * .5).toFloat(), (coord.lat * -4 + Constants.CH * .5).toFloat(), r, r)
        }

    }

}

fun main(args: Array<String>) {
    PApplet.main("com.azkdev.worldmapart.WorldMapArt")
}