package xyz.norlib.bank305.data

val fakeTansactions: List<Transaction> = listOf(
    Transaction(
        type = "ADD",
        amount = 200,
        date = "02/11/2024",
        destinataire = "Airtel Money",
        destinataireDetail = "077598300"
    ),
    Transaction(
        type = "REM",
        amount = 100,
        date = "06/11/2024",
        destinataire = "Moov Money",
        destinataireDetail = "062551649"
    ),
    Transaction(
        type = "SEN",
        amount = 50,
        date = "02/11/2024",
        destinataire = "Audrey MENGUE",
        destinataireDetail = ""
    ),
    Transaction(
        type = "ADD",
        amount = 200,
        date = "02/11/2024",
        destinataire = "Airtel Money",
        destinataireDetail = "077598300"
    ),
    Transaction(
        type = "REM",
        amount = 100,
        date = "06/11/2024",
        destinataire = "Moov Money",
        destinataireDetail = "062551649"
    ),
    Transaction(
        type = "SEN",
        amount = 50,
        date = "02/11/2024",
        destinataire = "Audrey MENGUE",
        destinataireDetail = ""
    ),
    Transaction(
        type = "ADD",
        amount = 200,
        date = "02/11/2024",
        destinataire = "Airtel Money",
        destinataireDetail = "077598300"
    ),
    Transaction(
        type = "REM",
        amount = 100,
        date = "06/11/2024",
        destinataire = "Moov Money",
        destinataireDetail = "062551649"
    ),
    Transaction(
        type = "SEN",
        amount = 50,
        date = "02/11/2024",
        destinataire = "Audrey MENGUE",
        destinataireDetail = ""
    ),
    Transaction(
        type = "ADD",
        amount = 200,
        date = "02/11/2024",
        destinataire = "Airtel Money",
        destinataireDetail = "077598300"
    ),
    Transaction(
        type = "REM",
        amount = 100,
        date = "06/11/2024",
        destinataire = "Moov Money",
        destinataireDetail = "062551649"
    ),
    Transaction(
        type = "SEN",
        amount = 50,
        date = "02/11/2024",
        destinataire = "Audrey MENGUE",
        destinataireDetail = ""
    ),
    Transaction(
        type = "ADD",
        amount = 200,
        date = "02/11/2024",
        destinataire = "Airtel Money",
        destinataireDetail = "077598300"
    ),
    Transaction(
        type = "REM",
        amount = 100,
        date = "06/11/2024",
        destinataire = "Moov Money",
        destinataireDetail = "062551649"
    ),
    Transaction(
        type = "SEN",
        amount = 50,
        date = "02/11/2024",
        destinataire = "Audrey MENGUE",
        destinataireDetail = ""
    ),
)

data class Transaction(
    val type: String,
    val amount: Int,
    val date: String,
    val destinataire: String,
    val destinataireDetail: String
)
