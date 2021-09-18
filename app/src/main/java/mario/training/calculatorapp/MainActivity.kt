package mario.training.calculatorapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    var lastNumeric = false
    var lastDot = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {

        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View) {

        tvInput.text =""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onEqual(view: View) {

        if (lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try{
                if(tvValue.startsWith(prefix = "-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                // for the calculations upon operators selection

                if(tvValue.contains(other = "-")){
                    val splitValue = tvValue.split("-")
                    var  one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if(tvValue.contains(other = "*")){
                    val splitValue = tvValue.split("*")
                    var  one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }else  if(tvValue.contains(other = "/")){
                    val splitValue = tvValue.split("/")
                    var  one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }  else if(tvValue.contains(other = "+")){
                    val splitValue = tvValue.split("+")
                    var  one = splitValue[0]
                    var two = splitValue[1]
                    if (prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

            }catch(e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if (result.contains(other = ".0"))
            value = result.substring(0,result.length -2)
            return  value
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String) :Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains(other = "/") || value.contains(other = "*")
                    || value.contains(other = "+") || value.contains(other = "-")
        }
    }
}