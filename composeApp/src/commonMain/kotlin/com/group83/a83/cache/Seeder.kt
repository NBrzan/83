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
                FlashcardModel(id = 0, front = "Dog", back = "An animal that barks"),
                FlashcardModel(id = 1, front = "Cat", back = "An animal that says meow"),
                FlashcardModel(id = 2, front = "Fish", back = "An animal that swims"),

                FlashcardModel(id = 3, front = "Red", back = "The color of an apple"),
                FlashcardModel(id = 4, front = "Blue", back = "The color of the sky"),
                FlashcardModel(id = 5, front = "Green", back = "The color of grass"),

                FlashcardModel(id = 6, front = "One", back = "Number 1"),
                FlashcardModel(id = 7, front = "Two", back = "Number 2"),
                FlashcardModel(id = 8, front = "Three", back = "Number 3"),

                FlashcardModel(id = 9, front = "Book", back = "Something you read"),
                FlashcardModel(id = 10, front = "Pen", back = "Used for writing"),
                FlashcardModel(id = 11, front = "Chair", back = "Used for sitting"),

                FlashcardModel(id = 12, front = "Shirt", back = "Clothing for your upper body"),
                FlashcardModel(id = 13, front = "Shoes", back = "Clothing for your feet"),
                FlashcardModel(id = 14, front = "Hat", back = "Clothing for your head"),

                FlashcardModel(id = 15, front = "Car", back = "Used to drive places"),
                FlashcardModel(id = 16, front = "Bus", back = "Takes many people to school"),
                FlashcardModel(id = 17, front = "Bicycle", back = "You ride it with pedals"),

                FlashcardModel(id = 18, front = "Hello", back = "A word to greet someone"),
                FlashcardModel(id = 19, front = "Goodbye", back = "A word to say when you leave")
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
                "What is the opposite of 'sad'?",
                listOf("Happy", "Angry", "Cold", "Sleepy"),
                listOf(0)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What are two animals?",
                listOf("Cat", "Run", "Dog", "Jump"),
                listOf(0, 2)
            )
            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What animal says 'meow'?",
                listOf("Dog", "Cat", "Cow", "Bird"),
                listOf(1)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "Which animal is big and gray?",
                listOf("Elephant", "Mouse", "Duck", "Frog"),
                listOf(0)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "Which two animals can fly?",
                listOf("Bird", "Cat", "Butterfly", "Fish"),
                listOf(0, 2)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What color is the sky?",
                listOf("Red", "Green", "Blue", "Yellow"),
                listOf(2)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What color are bananas?",
                listOf("Yellow", "Purple", "Black", "White"),
                listOf(0)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "Which of these is a color?",
                listOf("Dog", "Happy", "Pink", "Run"),
                listOf(2)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What number comes after three?",
                listOf("One", "Two", "Four", "Five"),
                listOf(2)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What do you write with?",
                listOf("Book", "Pen", "Chair", "Eraser"),
                listOf(1)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "Where do you sit?",
                listOf("Table", "Chair", "Bag", "Pencil"),
                listOf(1)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "What do you read?",
                listOf("Pen", "Book", "Chair", "Window"),
                listOf(1)
            )

            db.insertABCDQuestionWithAnswers(
                subject.id,
                "Which of these is number five?",
                listOf("3", "5", "7", "9"),
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
                "Type the missing word: 'I have ___ apple.'",
                null,
                listOf("an", "An")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "Type one animal that can fly.",
                null,
                listOf("bird")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "Write the name of an animal that lives in water.",
                null,
                listOf("fish", "whale", "dolphin")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "What color is grass?",
                null,
                listOf("green")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "Type the color of an apple.",
                null,
                listOf("red", "green")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "Write the number before ten.",
                null,
                listOf("nine", "9")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "How many fingers are on one hand?",
                null,
                listOf("five", "5")
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

            db.insertInputQuestionWithAnswers(
                subject.id,
                "What is on the picture?",
                "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Flh3.googleusercontent.com%2Fs5sYOSRoSEkmgjxGIKAwkXYcXXJvK7ORywoKg38oMFaZcTbiikToUpJEQrJHIQA0R277s79mniYk1TgIRVpNMm8Qklo-hZni1qWBUsPykNT8fJ7smVdX71MDTwp2LkUxMN5a5D_q&f=1&nofb=1&ipt=effec8b87e551a9b528535b60ca4ca0f3931256e566c295a8f0df06fcbf8b63a",
                listOf("phone", "smartphone", "mobile phone")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "What is on the picture?",
                "https://images.cdn.autocar.co.uk/sites/autocar.co.uk/files/styles/gallery_slide/public/mini-5-door-cooper.jpg?itok=JAsk5cnT",
                listOf("car")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "What is on the picture (answer with  words)?",
                "https://ih0.redbubble.net/image.106479213.6775/flat,800x800,075,f.jpg",
                listOf("sixteen")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "In one word describe the weather on the picture?",
                "https://c.pxhere.com/photos/cc/44/clouds_cloudy_countryside_dark_dark_clouds_dramatic_farm_field-1526807.jpg!d",
                listOf("cloudy")
            )

            db.insertInputQuestionWithAnswers(
                subject.id,
                "Which holiday is being decorated for in the picture?",
                "https://www.bankrate.com/2022/12/01142725/christmas-decorating-data.jpg?auto=webp",
                listOf("cloudy")
            )


        }
    }
}
