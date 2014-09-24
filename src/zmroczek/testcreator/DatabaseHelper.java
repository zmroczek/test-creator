package zmroczek.testcreator;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "testCreator";

    private static final String TABLE_TEST = "test";
    private static final String TABLE_TEST_ID = "id";
    private static final String TABLE_TEST_NAME = "name";

    private static final String TABLE_QUESTION = "question";
    private static final String TABLE_QUESTION_ID = "id";
    private static final String TABLE_QUESTION_TEST = "test";
    private static final String TABLE_QUESTION_QUESTION = "question";
    private static final String TABLE_QUESTION_ANSWERA = "answera";
    private static final String TABLE_QUESTION_ANSWERB = "answerb";
    private static final String TABLE_QUESTION_ANSWERC = "answerc";
    private static final String TABLE_QUESTION_ISCORRECTA = "isCorrectA";
    private static final String TABLE_QUESTION_ISCORRECTB = "isCorrectB";
    private static final String TABLE_QUESTION_ISCORRECTC = "isCorrectC";

    private SQLiteDatabase dataBase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase dataBase) {
        this.dataBase = dataBase;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_TEST + " ( "
                + TABLE_TEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_TEST_NAME + " TEXT UNIQUE)";
        dataBase.execSQL(sql);
        sql = "CREATE TABLE IF NOT EXISTS " + TABLE_QUESTION + " ( "
                + TABLE_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TABLE_QUESTION_TEST + " INTEGER, "
                + TABLE_QUESTION_QUESTION + " TEXT, "
                + TABLE_QUESTION_ANSWERA + " TEXT, "
                + TABLE_QUESTION_ANSWERB + " TEXT, "
                + TABLE_QUESTION_ANSWERC + " TEXT, "
                + TABLE_QUESTION_ISCORRECTA + " INTEGER, "
                + TABLE_QUESTION_ISCORRECTB + " INTEGER, "
                + TABLE_QUESTION_ISCORRECTC + " INTEGER, "
                + "FOREIGN KEY(" + TABLE_QUESTION_TEST + ") REFERENCES "
                + TABLE_TEST + "(" + TABLE_TEST_ID + "))";
        dataBase.execSQL(sql);
        addTests();
        addSampleQuestions();
    }

    private void addTests() {
        addTest("Example test");
    }

    public boolean addTest(String name) {
        ContentValues values = new ContentValues();
        values.put(TABLE_TEST_NAME, name);
        if (dataBase == null) {
            dataBase = this.getWritableDatabase();
        }
        if (!testExists(name)) {
            dataBase.insert(TABLE_TEST, null, values);
            return true;
        } else {
            return false;
        }
    }

    private boolean testExists(String testName) {
        String selectQuery = "SELECT * FROM " + TABLE_TEST + " WHERE " + TABLE_TEST_NAME + "='" + testName + "'";
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        return cursor.moveToFirst();
    }

    private void addSampleQuestions() {
        Question q1 = new Question("Who is the author of the novel \"Gone with the wind\"?",
                "George Orwell", "Margaret Mitchell", "Oscar Wilde", false, true, false,
                "Example test");
        this.addQuestion(q1);
        Question q2 = new Question("Who did discover America?",
                "Magellan", "Vespucci", "Columbus", false, false, true,
                "Example test");
        this.addQuestion(q2);
        Question q3 = new Question("Which of these are fruits?",
                "Tomato", "Banana", "Carrot", true, true, false,
                "Example test");
        this.addQuestion(q3);
    }

    public void addQuestion(Question quest) {
        String query = "SELECT " + TABLE_TEST_ID + " FROM "
                + TABLE_TEST + " WHERE " + TABLE_TEST_NAME + "='" + quest.getTest() + "'";
        if (dataBase == null) {
            dataBase = this.getWritableDatabase();
        }
        Cursor cursor = dataBase.rawQuery(query, null);
        String testId = "";
        if (cursor.moveToFirst()) {
            testId = cursor.getString(0);
        }
        ContentValues values = new ContentValues();
        values.put(TABLE_QUESTION_QUESTION, quest.getQuestion());
        values.put(TABLE_QUESTION_ANSWERA, quest.getAnswerA());
        values.put(TABLE_QUESTION_ANSWERB, quest.getAnswerB());
        values.put(TABLE_QUESTION_ANSWERC, quest.getAnswerC());
        values.put(TABLE_QUESTION_ISCORRECTA, quest.getIsCorrectA());
        values.put(TABLE_QUESTION_ISCORRECTB, quest.getIsCorrectB());
        values.put(TABLE_QUESTION_ISCORRECTC, quest.getIsCorrectC());
        values.put(TABLE_QUESTION_TEST, testId);
        this.dataBase.insert(TABLE_QUESTION, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase dataBase, int oldVersion,
                          int newVersion) {
        dataBase.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        dataBase.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
        onCreate(dataBase);
    }

    public List<Question> getAllQuestions(String testName) {
        List<Question> questionsList = new ArrayList<Question>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION + " WHERE "
                + TABLE_QUESTION_TEST + "=(SELECT " + TABLE_TEST_ID + " FROM "
                + TABLE_TEST + " WHERE " + TABLE_TEST_NAME + "='" + testName
                + "')";
        dataBase = this.getReadableDatabase();
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Question question = getQuestionFromCursor(cursor);
                questionsList.add(question);
            } while (cursor.moveToNext());
        }
        return questionsList;
    }

    private Question getQuestionFromCursor(Cursor cursor) {
        Question question = new Question();
        question.setTest(cursor.getString(1));
        question.setQuestion(cursor.getString(2));
        question.setAnswerA(cursor.getString(3));
        question.setAnswerB(cursor.getString(4));
        question.setAnswerC(cursor.getString(5));
        question.setIsCorrectA(cursor.getInt(6) == 1);
        question.setIsCorrectB(cursor.getInt(7) == 1);
        question.setIsCorrectC(cursor.getInt(8) == 1);
        return question;
    }

    public List<String> getAllTests() {
        List<String> testsList = new ArrayList<String>();
        String selectQuery = "SELECT  " + TABLE_TEST_NAME + " FROM " + TABLE_TEST;
        dataBase = this.getReadableDatabase();
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String testName = cursor.getString(0);
                testsList.add(testName);
            } while (cursor.moveToNext());
        }
        return testsList;
    }

    public boolean hasQuestions(String testName) {
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION + " WHERE "
                + TABLE_QUESTION_TEST + "=(SELECT " + TABLE_TEST_ID + " FROM "
                + TABLE_TEST + " WHERE " + TABLE_TEST_NAME + "='" + testName
                + "')";
        dataBase = this.getReadableDatabase();
        Cursor cursor = dataBase.rawQuery(selectQuery, null);
        return cursor.moveToFirst();
    }
}
