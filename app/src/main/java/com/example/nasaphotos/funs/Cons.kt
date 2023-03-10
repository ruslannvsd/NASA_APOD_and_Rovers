package com.example.nasaphotos.funs

object Cons {
    const val API_KEY = "B66P851vctAHXwDdkNWIdmru1WzQ5ZqCyGN9aBsk"
    const val BASE_URL = "https://api.nasa.gov"
    const val CURIOSITY = "curiosity"
    const val PERSEVERANCE = "perseverance"
    const val OPPORTUNITY = "opportunity"
    const val SPIRIT = "spirit"
    const val SOLS_OPP = 5111 // the Opportunity is no more active on Mars so it has a fixed number of sols
    const val SOLS_SPI = 2208 // the same with the Spirit
    const val COUNT = "9" // 9 images of apods
    const val NONE = "NONE"
    const val IMAGE = "image"

    const val SHARED_APOD = "SharedApod"
    const val SAL = "SAL" // shared apod list
}

// https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY