package com.app.cabi.model

public class Model {

    lateinit var idNation: String
    lateinit var Nation: String
    lateinit var IDYear: String
    lateinit var Year: String
    lateinit var Population: String
    lateinit var SlugNation: String

    constructor(idNation: String, Nation: String, IdYear: String, Year: String, Population: String, SlugNation: String) {
        this.idNation = idNation
        this.Nation = Nation
        this.IDYear = IdYear
        this.Year = Year
        this.Population = Population
        this.SlugNation = SlugNation
    }

    constructor()
}