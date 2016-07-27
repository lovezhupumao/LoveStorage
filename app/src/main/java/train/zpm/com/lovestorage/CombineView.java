package train.zpm.com.lovestorage;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/*
复合控件类：包括一个ImageView和一个TextView
 */

public class CombineView extends LinearLayout {

	private ImageView mImageView;
	private TextView mTextView;
	public CombineView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//自定义复合控件属性包括ImageView显示图片属性和TextView显示内容属性
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.combineview);
		LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.view_combine, this, true);
        mImageView=(ImageView)findViewById(R.id.combine_imageview);
        mTextView=(TextView)findViewById(R.id.combine_textview);
        mImageView.setImageResource(typedArray.getResourceId(R.styleable.combineview_imagebg, R.drawable.setting));
        mTextView.setText(typedArray.getString(R.styleable.combineview_textcontext));
        
	}
}
