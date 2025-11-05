package com.group83.a83.cache

import com.group83.a83.model.FlashcardModel

class DatabaseSeeder(private val db: FullDatabase) {

    fun seed() {
        val subjects = listOf("English Vocabulary")



        // Insert subject if none exist
        if (db.getAllSubjects().isEmpty()) {
            subjects.forEach { db.insertSubject(it) }
        }else {
            return
        }

        val allSubjects = db.getAllSubjects()

        // Flashcards
        db.clearAndCreateCards(
            listOf(
                FlashcardModel(id = 0, front = "Abundant", back = "Existing or available in large quantities"),
                FlashcardModel(id = 1, front = "Benevolent", back = "Well meaning and kindly"),
                FlashcardModel(id = 2, front = "Candid", back = "Truthful and straightforward"),
                FlashcardModel(id = 3, front = "Diligent", back = "Having or showing care in work or duties"),
                FlashcardModel(id = 4, front = "Eloquent", back = "Fluent or persuasive in speaking or writing"),
                FlashcardModel(id = 5, front = "Frugal", back = "Sparing or economical with resources"),
                FlashcardModel(id = 6, front = "Gregarious", back = "Fond of company; sociable"),
                FlashcardModel(id = 7, front = "Hinder", back = "Create difficulties resulting in delay"),
                FlashcardModel(id = 8, front = "Impartial", back = "Treating all rivals equally; fair"),
                FlashcardModel(id = 9, front = "Jubilant", back = "Feeling or expressing great happiness")
            )
        )

        allSubjects.forEach { subject ->
            // ABCD Questions
            db.insertABCDQuestionWithAnswers(
                subject.id,
                "Which of these are occupations?",
                listOf("Doctor", "Running", "Teacher", "Quickly"),
                listOf(0, 2)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "Which of the following is a synonym for 'happy'?",
                listOf("Sorrowful", "Joyful", "Angry", "Tired"),
                listOf(1)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What is the plural of 'mouse'?",
                listOf("Mouses", "Mice", "Mooses", "Meece"),
                listOf(1)
            )

            // ABCD Image Questions
            db.insertABCDImageQuestionWithAnswers(
                subject.id,
                "What is on the picture?",
                "https://wallpapers.com/images/high/bread-pictures-4lmls4s8nvsmnvmd.webp",
                listOf("Bread", "Microwave", "Plumber", "PC"),
                listOf(0)
            )

            db.insertABCDImageQuestionWithAnswers(
                subject.id,
                "What animal is in the picture?",
                "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                listOf("Dog", "Cat", "Bird", "Fish"),
                listOf(1)
            )

            // Input Questions
            db.insertInputQuestionWithAnswers(
                subject.id,
                "What is the past tense of the verb 'go'?",
                null,
                listOf("went", "Went")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "Which word is a noun: 'run', 'quickly', or 'book'?",
                null,
                listOf("book", "Book")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "Type the missing word: 'I have ___ apple.'",
                null,
                listOf("an", "An")
            )

            // Input Image Questions
            db.insertInputQuestionWithAnswers(
                subject.id,
                "What is on the picture?",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fstatic.vecteezy.com%2Fsystem%2Fresources%2Fpreviews%2F009%2F347%2F983%2Foriginal%2Fbig-pile-of-us-dollar-notes-a-lot-of-money-over-transparent-background-3d-rendering-of-bundles-of-cash-png.png&f=1&nofb=1&ipt=08f5ae4c5c0390651253a677c0649441b11e853360c14e5f0f14913e8dee11f6",
                listOf("money", "cash", "moolah", "Money", "Cash")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "What is the name of this famous landmark?",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fthumbs.dreamstime.com%2Fb%2Fpalace-westminster-big-ben-night-london-england-uk-50452797.jpg&f=1&nofb=1&ipt=68ea967b2caa5a41f73597448901146611f876894bcf600c631ba12c17f04c01",
                listOf("Big Ben", "big ben")
            )
        }
    }
}
