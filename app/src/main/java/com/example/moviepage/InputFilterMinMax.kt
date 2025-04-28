import android.text.InputFilter
import android.text.Spanned

class InputFilterMinMax(private val min: Int, private val max: Int) : InputFilter {

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val newVal = dest.toString().substring(0, dstart) + source.toString() + dest.toString().substring(dend)
            val input = newVal.toInt()
            if (isInRange(min, max, input)) {
                return null // Acceptable
            }
        } catch (e: NumberFormatException) {
            // Ignore parse error
        }
        return "" // Reject
    }

    private fun isInRange(min: Int, max: Int, value: Int): Boolean {
        return value in min..max
    }
}
