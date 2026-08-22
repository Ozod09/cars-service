package uz.taxi.cars_service.base;

public interface Messages {
    String SUCCESS = "success";
    String DELETED_SUCCESSFULLY = "Muvaffaqiyatli o’chirildi";


    String TYPE_SERVICE_NOT_FOUND = "Type of service not found";
    String CARCLASS_NOT_FOUND = "CarsClass topilmadi";
    String DATA_NOT_FOUND = "Maʼlumotlar topilmadi";
    String TARIFF_NOT_FOUND = "Tariff topilmaid";
    String PHONE_ALREADY_EXISTS = "Bu telefon raqami allaqachon ro’yxatdan o’tgan";

    // Code verification messages
    String CODE_TIMED_OUT = "Kodni kiritish muddati tugadi";
    String CONFIRMATION_CODE_INPUT_LIMIT_EXCEEDED = "Tasdiqlash kodini kiritish chegarasidan oshib ketdi";
    String INVALID_CODE_ENTERED_TRY_AGAIN = "Noto’g’ri kod kiritildi. Iltimos, qayta urinib koʻring";
    String CODE_SEND_PHONE = "Nomerga otp kod yuborildi.";

    // PIN code messages
    String PIN_CODE_REQUIRED = "PIN kod kerak";
    String PIN_CODE_INCORRECT = "PIN kod noto’g’ri";
    String INVALID_PIN_CODE = "PIN kod o’chirilgan";

    // Phone messages
    String PHONE_NUMBER_REQUIRED = "Telefon raqami shart";
    String INVALID_PHONE_NUMBER = "Telefon raqami yaroqsiz";
    String PHONE_NUMBER_OR_PASSWORD_INCORRECT = "Telefon raqami yoki parol noto’g’ri";

    // Token messages
    String INVALID_TOKEN = "Token yaroqsiz";

    // Password messages
    String PASSWORDS_DO_NOT_MATCH = "Parollar bir-biriga mos kelmaydi";
    String OLD_PASSWORDS_DO_NOT_MATCH = "Eski parol mos kelmayapti";

    // Other messages
    String UNKNOWN_ERROR = "Noma’lum xato";
    String SIGN_INIT_NOT_FOUND = "Malumot topilmadi";
    String SUPER_ADMIN_MOBILE_LOGIN_NOT_ALLOWED = "Super Adminga mobilga login qilish mumkin emas";
}
