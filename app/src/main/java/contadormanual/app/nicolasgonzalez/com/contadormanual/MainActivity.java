package contadormanual.app.nicolasgonzalez.com.contadormanual;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    public int contador;
    TextView resultado;
    @Override
    protected void onCreate(Bundle estado) {
        super.onCreate(estado);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        resultado =  (TextView)findViewById(R.id.cuenta);
        contador = 0;
        TextView.OnEditorActionListener teclado; //teclado es un evento de desplegar el teclado que se analiza, para detectar su activación
        teclado = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int accion, KeyEvent evento) {
                if(accion  == EditorInfo.IME_ACTION_DONE){ //Cuando se pulsa la tecla LISTO del teclado, el programa va a reset para comenzar a contar desde el valor agregado
                    reset(null);
                }
                return false;
            }
        };
        EditText set = (EditText) findViewById(R.id.set); //De aqui para abajo, el programa desplega o cierra el telcado cuando se deja de apuntar
        //en la parte de ingreso del número
        set.setOnEditorActionListener(teclado);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //SOFT_INPUT_STATE_ALWAYS_HIDDEN mantiene el teclado guardado a menos que se toque el EditText
    }
    public void incrementar (View vista){
        contador++;
        resultado.setText(""+contador); //set text pone el número ingresado y lo suma con el valor de contador
    }
    public void decrementar (View vista){
        contador--;
        if (contador < 0){ //Este if verifica que la casilla de números negativos esté marcada para que el contador funcione con negativos
            CheckBox negativos =  (CheckBox)findViewById(R.id.negativos);
            if (!negativos.isChecked()) contador=0; //Si la casilla no es verificada y contador <0, entonces contador = 0
        }
        resultado.setText(""+contador);
    }

    public void reset (View vista){
        EditText e = (EditText)findViewById(R.id.set); //se define el objeto e de tipo editText, para poder editar el número de inicio de la aplicación
        try { //Si el valor en la caja es un entero, toma el texto Integer.parseInt
            contador = Integer.parseInt(e.getText().toString());
        }catch (Exception excepcion){
            contador = 0; //Si el texto ingresado no es un entero, el contador se pone a cero (Para evitar que el programa se cuelgue al recibir argumentos no validos)
        }
        e.setText("");
        resultado.setText(""+contador);
        InputMethodManager im = (InputMethodManager)
                getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(e.getWindowToken(),0);
    }
}
