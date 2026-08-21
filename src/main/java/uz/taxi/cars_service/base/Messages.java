package uz.taxi.cars_service.base;

public interface Messages {
    String SUCCESS = "success";
    String DELETED_SUCCESSFULLY = "Muvaffaqiyatli o’chirildi";

    // Client messages
    String CLIENT_ID_NOT_FOUND = "Client id yuborilmadi";

    // Driver messages
    String DRIVER_ID_NOT_FOUND = "Driver id yuborilmadi";
    String DRIVER_NOT_FOUND = "Haydovchi topilmadi";
    String PASSPORT_ALREADY_EXISTS = "Bu passport raqami allaqachon ro’yxatdan o’tgan";

    // User messages
    String VERIFICATION_STATUS_NOT_CORRECT = "Tasdiqlash holati toʻgʻri emas";
    String USER_BLOCKED = "Foydalanuvchi bloklangan";
    String INCORRECT_PASSWORD = "Noto’g’ri parol";
    String USER_NOT_FOUND = "Foydalanuvchi topilmadi";
    String USER_ALREADY_EXISTS = "Foydalanuvchi allaqachon ma’lumotlar bazasida mavjud";
    String USER_SUCCESSFULLY_REGISTERED = "Foydalanuvchi muvaffaqiyatli ro’yxatdan o’tdi";
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
