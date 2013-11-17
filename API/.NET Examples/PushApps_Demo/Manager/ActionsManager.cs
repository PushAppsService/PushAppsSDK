using Newtonsoft.Json;
using PushApps_Demo.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace PushApps_Demo.Manager
{
    public class ActionsManager
    {
        public static void SendRegisterRequest(Register_Demo reg)
        {
            string registerMethod = "RemoteAPI/RegisterDevice";
            if (reg.validate()) // if validation is OK and we configured all the nececcery fields, we can continue.
            {
                SendRequest(registerMethod, (Object)reg);
            }
        }

        public static void SendUnregisterRequest(Unregister_Demo unreg)
        {
            string registerMethod = "RemoteAPI/UnregisterDevice";
            if (unreg.validate()) // if validation is OK and we configured all the nececcery fields, we can continue.
            {
                SendRequest(registerMethod, (Object)unreg);
            }
        }

        public static void SendPushNotificationRequest(PushNotification_Demo notif)
        {
            string registerMethod = "RemoteAPI/CreateNotification";
            string payloadMethod = "RemoteAPI/CheckIosPayload";
            if (notif.validate()) // if validation is OK and we configured all the nececcery fields, we can continue.
            {   
                //It is highly recommanded to check iOS payload before sending a notication.
                if (notif.Platforms == null || notif.Platforms.Contains(Enums.DevicePlatform.iOS))
                {   // if platforms is null (means sends to all platforms) or IOS is configured within the platforms
                    // user should use this method if the iOS is configured in the application he sends push to in order to
                    // prevent an error response when sending notification with high payload (ios payload is limited to 255 Bytes).
                    // If Ios is not configured to the current app, iOS payload isn't being checked.
                    Payload_Demo payload = new Payload_Demo();
                    payload.Message = notif.Message;
                    payload.Link = notif.Link;
                    payload.CustomJsonKey = notif.CustomJsonKey;
                    payload.CustomJson = notif.CustomJson;
                    PushAppResponse resp = new PushAppResponse();
                    resp = SendRequest(payloadMethod, (Object)payload);
                    if (resp.Code.Equals("100"))        // 100 stands for Success
                    {
                        SendRequest(registerMethod, (Object)notif);
                    }
                }
                else
                {
                    SendRequest(registerMethod, (Object)notif);
                }
            }
        }
        



        public static PushAppResponse SendRequest(string methodLink, Object Request)
        {
            //The response from PushApps server is as followed : 
            //{
            //  "Code": "Our code for error or success ",
            //  "Data": "In case data needs to be returned, it will stored in here",
            //  "Message": "The code's message will appear in here"
            //}
            string url = "https://ws.pushapps.mobi/" + methodLink;
            string CurrentMethod = System.Reflection.MethodBase.GetCurrentMethod().Name;
            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(url);
                request.Method = "POST";
                request.ContentType = "application/json; charset=utf-8";
                string json = JsonConvert.SerializeObject(Request,
                                              new JsonSerializerSettings
                                              {
                                                  NullValueHandling = NullValueHandling.Ignore,
                                                  Formatting = Newtonsoft.Json.Formatting.None
                                              });
                StreamWriter writer = new StreamWriter(request.GetRequestStream());
                Console.WriteLine("This is the json you are now sending : " + json);
                writer.Write(json);
                writer.Close();
                System.Net.WebResponse resp = request.GetResponse();
                if (resp == null) return new PushAppResponse { Code = "0", Message = "response is null" };
                System.IO.StreamReader sr = new System.IO.StreamReader(resp.GetResponseStream());
                string jsonString = sr.ReadToEnd();
                Console.WriteLine("This is the answer from PushApps server : " + jsonString);
                PushAppResponse result = JsonConvert.DeserializeObject<PushAppResponse>(jsonString);
                return result;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.StackTrace);
                return new PushAppResponse { Code = "0" };
            }
        }
    }
}
