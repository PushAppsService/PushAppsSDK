<?php
    $url = "https://ws.pushapps.mobi/RemoteAPI/CreateNotification";
    $data = array(
                  'SecretToken'      => 'XXXXXXXXXX', ## Your app secret token
                  'Message' => 'This is the content of the message', ## The message you want to send
                  'Platforms' => array(1, 2), ## Optional, platforms to send to, if empty will send to all configured platforms
                  'Devices' => array(array(
                                           'PushToken' => 'XXXXXXXX', ## Device push token
                                     'DeviceType' => 2
                                           )) ## Optional, array of devices to send, if empty will send to all registered users
                  );
    
    
    $content = json_encode($data);
    
    $curl = curl_init($url);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_HTTPHEADER,
                array("Content-type: application/json"));
    curl_setopt($curl, CURLOPT_POST, true);
    curl_setopt($curl, CURLOPT_POSTFIELDS, $content);
    
    $json_response = curl_exec($curl);
    
    $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
    
    if ( $status != 200 ) {
        die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
    }
    
    curl_close($curl);
    
    echo "Response: " . $json_response . "\n";
	?>