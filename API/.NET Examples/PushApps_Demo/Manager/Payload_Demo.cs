using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PushApps_Demo.Manager
{
    public class Payload_Demo
    {
        public string Message { get; set; }     
        public string Link { get; set; }            //optional.
        public string CustomJsonKey { get; set; }   //optional.
        public string CustomJson { get; set; }      //optional.

        //we dont need to validate payload, it is part of the notification. if notification validate is OK , so as Payload.

    }
}
