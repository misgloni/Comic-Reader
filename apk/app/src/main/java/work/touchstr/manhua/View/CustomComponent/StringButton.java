package work.touchstr.manhua.View.CustomComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class StringButton extends android.support.v7.widget.AppCompatButton {
    String str="";
    public StringButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setString(String str)
    {
        this.str=str;
    }
    public String getString()
    {
        return str;
    }
}
