package com.example.geoquizz

class Question (private var mTextResId: Int, private var mAnswerTrue: Boolean) {
    fun getTextResId(): Int = mTextResId
    fun getAnswerTrue(): Boolean = mAnswerTrue
    fun setTextResId(TextResId: Int) {
        mTextResId = TextResId
    }
    fun setAnswerTrue(AnswerTrue: Boolean) {
        mAnswerTrue = AnswerTrue
    }
}