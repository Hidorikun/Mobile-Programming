package com.example.george.firstapp

class BookRepository {

    companion object {
        var books = listOf (
            Book(1, "Red Rising", 5, "It has been seven hundred years since mankind colonized other planets. The powerful ruling class of humans has installed a rigid, color-based social hierarchy where the physically superior Golds at the top rule with an iron fist. Sixteen-year-old Darrow is a Red, a class of workers who toil beneath the surface of Mars mining helium-3 to terraform the planet and make it habitable. He and his wife Eo are captured after leaving a forbidden area and are arrested. While she is publicly whipped for her crime, Eo sings a forbidden folk tune as a protest against their virtual enslavement. She is subsequently hanged on the orders of Mars' ArchGovernor Nero au Augustus. Darrow cuts down and buries his wife's body, a crime for which he is also hanged. However, Darrow awakes to find that he has been drugged and delivered into the hands of the Sons of Ares, a terrorist group of Reds who fight against the oppression of the \"lowColors\". They have adopted the video of Eo's song and execution as a rallying vehicle for their cause. Darrow joins the Sons when he learns that Mars was already terraformed centuries before and that the Reds have been tricked into perpetual servitude and subjugation."),
            Book(2, "Golden Son", 4, "The iron rain"),
            Book(3, "Morning Star", 3, "Society tumbles"),
            Book(4, "Dracula", 4, "Your lord awaits in slumber"),
            Book(5, "The art of thinking clearly ", 4, "99 problems and how to solve them ")
        )

        fun get(id: Int) = books.filter { it.id == id }.firstOrNull()

    }
}