import day05.Pos
import day05.seg2
import day05.toSeg

"0,9 -> 5,9".split(" -> ").map {
    val coordinates = it.split(",")
    Pos(coordinates[0].toInt(), coordinates[1].toInt())
}

toSeg("1,1 -> 3,3", ::seg2)

toSeg("9,7 -> 7,9", ::seg2)
