/*
 * @Date: 2022-08-30 09:52:36
 * @LastEditors: dengxin 994386508@qq.com
 * @LastEditTime: 2022-08-30 14:37:31
 * @FilePath: /untitled1/example/lib/main.dart
 */
import 'package:flutter/material.dart';
import 'package:print_flutter_plugin/print_flutter_plugin.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _untitled1Plugin = PrintFlutterPlugin();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: TextButton(
              onPressed: () async {
                await _untitled1Plugin.init();
                await _untitled1Plugin.printLine(5);
                await _untitled1Plugin.printText(PrintTextVo(text: "测试打印"));
                await _untitled1Plugin.printText(PrintTextVo(text: "测试打印"));
                await _untitled1Plugin.printQR(PrintQRVo(text: "测试打印二维码"));
                await _untitled1Plugin.start();
              },
              child: Text("测试")),
        ),
      ),
    );
  }
}
