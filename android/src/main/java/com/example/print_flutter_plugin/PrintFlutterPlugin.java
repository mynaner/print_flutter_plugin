package com.example.print_flutter_plugin;
import android.app.Activity;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.lc_print_sdk.PrintUtil;
import java.util.Map;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** PrintFlutterPlugin */
public class PrintFlutterPlugin implements FlutterPlugin, ActivityAware, MethodCallHandler, PrintUtil.PrinterBinderListener {
  private MethodChannel channel;
  private Activity activity;
  private static final String TAG = "打印机";
  PrintUtil printUtil;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "print_flutter_plugin");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {



    final Map<String, Object> arguments = call.arguments();
    switch (call.method){
      // 初始化
      case "init":
        initPrint(result);
        break;
      // 开始打印
      case "start":
        start(result);
        break;
      // 打印行数 换行 或者空白行数
      case "printLine":
        int line = (int) arguments.get("line");

        printLine(result,line);
        break;
//        文字
      case "printText":
//        printUtil.printText (PrintConfig.Align.ALIGN_CENTER, PrintConfig.FontSize.TOP_FONT_SIZE_MIDDLE, false, false, "------------------------------------------------------\n\n");
        int align = (int) arguments.get("align");
        int size = (int) arguments.get("size");
        boolean isBold = (boolean) arguments.get("isBold");
        boolean isUnderLine = (boolean) arguments.get("isUnderLine");
        String text = (String) arguments.get("text");
        printText(result,align,size,isBold,isUnderLine,text);
        break;
//        二维码
      case "printQR":
        int offset = (int) arguments.get("offset");
        int height = (int) arguments.get("height");
        text = (String) arguments.get("text");
        printQR(result,offset,height,text);
        break;
      default:
        result.notImplemented();
        break;
    }
  }



  //  初始化
  public void initPrint(Result result){
    try {
      printUtil=PrintUtil.getInstance (activity.getApplicationContext());
      printUtil.setPrintEventListener(this);
      Log.i(TAG, "initPrint: 初始化成功");
      result.success(true);
    }catch (Exception e){
      result.error(TAG,"初始化失败",null);
    }
  }
  //  打印
  public void start(Result result){
    try {
      Log.i(TAG, "start: 开始打印");
      printUtil.start();
      result.success(true);
    }catch (Exception e){
      result.error(TAG,"打印失败",e);
    }
  }
  //  设置空白行
  public void printLine(Result result,int line){
    try {
      printUtil.printLine(line);
      result.success(true);
    }catch (Exception e){
      result.error(TAG,"设置空白行",e);
    }
  }

  //  printText
  private void printText(Result result, int align, int size, boolean isBold, boolean isUnderLine, String text) {
    try {
      printUtil.printText(align,size,isBold,isUnderLine,text);
      result.success(true);
    }catch (Exception e){
      result.error(TAG,"printText",e);
    }
  }


  private void printQR(Result result,int offset, int height, String content){
    try {
      printUtil.printQR(offset,height,content);
      result.success(true);
    }catch (Exception e){
      result.error(TAG,"printQR",e);
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }


  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {
    this.onDetachedFromActivity();

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    this.onAttachedToActivity(binding);
  }

  @Override
  public void onDetachedFromActivity() {
    activity = null;
    printUtil=null;
  }

  @Override
  public void onPrintCallback(int i) {

  }

  @Override
  public void onVersion(String s) {

  }
}
