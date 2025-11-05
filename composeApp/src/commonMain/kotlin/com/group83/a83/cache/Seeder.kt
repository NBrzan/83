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
                "https://www.bbc.co.uk/food/collections/brilliant_breads",
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
                "https://www.publicdomainpictures.net/view-image.php?image=165194&picture=i-soldi",
                listOf("money", "cash", "moolah", "Money", "Cash")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "What is the name of this famous landmark?",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/Big_Ben_in_London_2015.jpg/800px-Big_Ben_in_London_2015.jpg",
                listOf("Big Ben", "big ben")
            )
        }
    }
}
