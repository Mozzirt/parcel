import 'package:device_info_plus/device_info_plus.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'dart:io';
import 'package:flutter/services.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:parcel/SplashScreen.dart';
import 'package:webview_flutter/webview_flutter.dart';
import 'dart:async';

class ViewWidget extends StatefulWidget {

  @override
  _ViewState createState() => _ViewState();
}

class _ViewState extends State<ViewWidget> {
  var serverURL = "http://localhost:8080";
  bool isLoading = true;
  late WebViewController _webViewController;
  late String _url;

  @override
  void initState() {
    super.initState();
    // Enable hybrid composition.
    if (Platform.isAndroid) WebView.platform = SurfaceAndroidWebView();
  }

  @override
  Widget build(BuildContext context) {
    SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]);
    return WillPopScope(
      child: Scaffold(
        body: SafeArea(
          child: Stack(
            children: [
              WebView(
                initialUrl: serverURL,
                onWebViewCreated: (WebViewController webViewController) {
                  _webViewController = webViewController;
                },
                javascriptMode: JavascriptMode.unrestricted,
                javascriptChannels: <JavascriptChannel>{
                  _baseJavascript(context),
                },
                debuggingEnabled: true,
                // debug

                onPageStarted: (url) {
                  _url = url;

                  setState(() {
                    isLoading = true;
                  });
                },
                onPageFinished: (url) {
                  _url = url;
                  print("page loaded = $url");

                  // 기기 고유번호
                  getDeviceUniqueId().then((value) =>  {
                    _webViewController.evaluateJavascript(value)
                  });

                  setState(() {
                    isLoading = false;
                  });
                },
              ),
              isLoading ? const Center(child: CircularProgressIndicator(),) // TODO 임시 서큘러로딩
                  : Stack(),
            ],
          ),
        ),
      ),

      onWillPop: () {
        var future = _webViewController.canGoBack();
        future.then((canGoBack) {
          if (canGoBack) {
            // 메인에서 뒤로가기
            if (_url == ("$serverURL/main")) {
              if (Platform.isAndroid) {
                showDialog(
                    context: context,
                    builder: (context) =>
                        AlertDialog(
                          title: const Text('앱종료이벤트'),
                          actions: <Widget>[
                            FlatButton(
                              onPressed: () {
                                Navigator.of(context).pop();
                              },
                              child: const Text('취소'),
                            ),
                            FlatButton(
                              onPressed: () {
                                SystemNavigator.pop();
                              },
                              child: const Text('종료'),
                            ),
                          ],
                        ));
              }
            } else {
              _webViewController.goBack();
            }
          }
        });
        return Future.value(false);
      },
    );
  }


  JavascriptChannel _baseJavascript(BuildContext context) {
    return JavascriptChannel(
        name: 'Print',
        onMessageReceived: (JavascriptMessage message) async {
          if (message.message == "developer") { // 개발자 화면으로 이동시킴
            Navigator.push(
              context, MaterialPageRoute(builder: (context) => SplashScreen()),);  // TODO 수정해야함
          } else {
            Fluttertoast.showToast(
                msg: message.message,
                // 토스트 메시지 내용
                gravity: ToastGravity.BOTTOM,
                backgroundColor: Colors.grey,
                fontSize: 14.0,
                textColor: Colors.white,
                toastLength: Toast.LENGTH_SHORT // 토스트 메시지 지속시간 짧게
            );
          }
        });
  }


  Future<String> getDeviceUniqueId() async {
    var deviceUUID = 'none';
    String? deviceModel = 'none';
    var deviceInfo = DeviceInfoPlugin();
    if (Platform.isAndroid) {
      var androidInfo = await deviceInfo.androidInfo;
      deviceUUID = androidInfo.id!;
      deviceModel = androidInfo.model!;
    } else if (Platform.isIOS) {
      var iosInfo = await deviceInfo.iosInfo;
      deviceUUID = iosInfo.identifierForVendor!;
      deviceModel = iosInfo.model!;
    } else if (kIsWeb) {
      var webInfo = await deviceInfo.webBrowserInfo;
      deviceUUID = webInfo.vendor! +
          webInfo.userAgent! +
          webInfo.hardwareConcurrency.toString();
      deviceModel = webInfo.browserName.toString(); // enum
    }
    var deviceFunction = 'deviceInfo("$deviceUUID","$deviceModel")';
    return deviceFunction;
  }
}

