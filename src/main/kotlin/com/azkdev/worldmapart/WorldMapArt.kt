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

    override fun settings() {
        size(Constants.CW.toInt(), Constants.CH.toInt())
    }

    override fun setup() {
        background(Constants.BG)
        mapper = jacksonObjectMapper()
        cities = mapper.readValue(FileInputStream(this.javaClass.getResource("/data/city.list.json").file))
        drawableCities = arrayListOf()
        cities.forEach {
            val city = City(it)
            drawableCities.add(city)
            city.draw()
        }
    }

    override fun draw() {

    }

    inner class City(
            val id: Int,
            val name: String,
            val country: String,
            val coord: Coord
    ) {

        constructor(dataCity: DataCity) : this(dataCity.id, dataCity.name, dataCity.country, dataCity.coord)

        fun draw() {
            noStroke()
            fill(255F, 0F, 0F)
            ellipse(coord.lon, coord.lat, 2F, 2F)
        }

    }

}

fun main(args: Array<String>) {
    PApplet.main("com.azkdev.worldmapart.WorldMapArt")
}