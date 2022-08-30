
import 'package:flutter/services.dart';


class PrintFlutterPlugin {
  final methodChannel = const MethodChannel('print_flutter_plugin');

  Future<bool?> init() async {
  return await methodChannel.invokeMethod<bool>('init');
  }

  Future<bool?> start() async {
  return await methodChannel.invokeMethod<bool>('start');
  }

  Future<bool?> printLine(int line) async {
  return await methodChannel.invokeMethod<bool>('printLine', {"line": line});
  }

  ///
  /// align 2; 居中   1; 居左 3; 居右
  /// size  1 - 8
  ///
  Future<bool?> printText(PrintTextVo params) async {
  print(params.toJson());
  return await methodChannel.invokeMethod<bool>('printText', params.toJson());
  }

  Future<bool?> printQR(PrintQRVo params) async {
  print(params.toJson());
  return await methodChannel.invokeMethod<bool>('printQR', params.toJson());
  }
  }

  class PrintTextVo {
  int align = 1;
  int size = 3;
  bool isBold = false;
  bool isUnderLine = false;
  String? text;

  PrintTextVo(
  {this.align = 1,
  this.size = 3,
  this.isBold = false,
  this.isUnderLine = false,
  this.text});

  PrintTextVo.fromJson(Map<String, dynamic> json) {
  align = json['align'];
  size = json['size'];
  isBold = json['isBold'];
  isUnderLine = json['isUnderLine'];
  text = json["text"];
  }

  Map<String, dynamic> toJson() {
  final data = <String, dynamic>{};

  data['align'] = align;
  data['size'] = size;
  data['isBold'] = isBold;
  data['isUnderLine'] = isUnderLine;
  data['text'] = text;

  return data;
  }
  }

  class PrintQRVo {
  int offset = 2;
  int height = 384;
  String? text;

  PrintQRVo({this.offset = 1, this.height = 384, this.text});

  PrintQRVo.fromJson(Map<String, dynamic> json) {
  offset = json['offset'];
  height = json['height'];
  text = json["text"];
  }

  Map<String, dynamic> toJson() {
  final data = <String, dynamic>{};

  data['offset'] = offset;
  data['height'] = height;
  data['text'] = text;

  return data;
  }

}
