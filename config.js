/* Magic Mirror Config Sample
 *
 * By Michael Teeuw https://michaelteeuw.nl
 * MIT Licensed.
 *
 * For more information on how you can configure this file
 * see https://docs.magicmirror.builders/getting-started/configuration.html#general
 * and https://docs.magicmirror.builders/modules/configuration.html
 */
let config = {
    address: "localhost",  // Address to listen on, can be:
                           // - "localhost", "127.0.0.1", "::1" to listen on loopback interface
                           // - another specific IPv4/6 to listen on a specific interface
                           // - "0.0.0.0", "::" to listen on any interface
                           // Default, when address config is left out or empty, is "localhost"
    port: 8080,
    basePath: "/",  // The URL path where MagicMirror is hosted. If you are using a Reverse proxy
                    // you must set the sub path here. basePath must end with a /
    ipWhitelist: ["127.0.0.1", "::ffff:127.0.0.1", "::1"],  // Set [] to allow all IP addresses
                                                            // or add a specific IPv4 of 192.168.1.5 :
                                                            // ["127.0.0.1", "::ffff:127.0.0.1", "::1", "::ffff:192.168.1.5"],
                                                            // or IPv4 range of 192.168.3.0 --> 192.168.3.15 use CIDR format :
                                                            // ["127.0.0.1", "::ffff:127.0.0.1", "::1", "::ffff:192.168.3.0/28"],
    useHttps: false,  // Support HTTPS or not, default "false" will use HTTP
    httpsPrivateKey: "",  // HTTPS private key path, only require when useHttps is true
    httpsCertificate: "",  // HTTPS Certificate path, only require when useHttps is true

    language: "ko",
    locale: "en-US",
    logLevel: ["INFO", "LOG", "WARN", "ERROR"], // Add "DEBUG" for even more logging
    timeFormat: 24,
    units: "metric",
    // serverOnly:  true/false/"local" ,
    // local for armv6l processors, default
    //   starts serveronly and then starts chrome browser
    // false, default for all NON-armv6l devices
    // true, force serveronly mode, because you want to.. no UI on this device

    modules: [
        {
            module: "alert",
        },
        {
            module: "updatenotification",
            position: "top_bar"
        },
        {
            module: "clock",
            position: "top_left"
        },
        {
            module: 'MMM-MonthCalendar',
            position: "top_left",
            header: "",
            config: {
                updateDelay: 60,
                showAdjacentMonths: true
            }
        },
        {
            module: "calendar",
            header: "schedule",
            position: "top_left",
            config: {
                calendars: [
                    {
                        symbol: "calendar-check",
                        url: "https://calendar.google.com/calendar/ical/ksm020421%40gmail.com/private-e022bfc9058e75c2d2d3458c88d7812d/basic.ics"
                    }
                ]
            }
        },
        {
            module: "weather",
            position: "top_right",
            config: {
                weatherProvider: "openweathermap",
                type: "current",
                location: "Suwon",
                locationID: "1835553", //ID from http://bulk.openweathermap.org/sample/city.list.json.gz; unzip the gz file and find your city
                apiKey: "9030b6637989c1b9f6ecec80a4838d11",
                onlyTemp: true
            }
        },
        {
            module: "weather",
            position: "top_right",
            header: "welcome",
            config: {
                weatherProvider: "openweathermap",
                type: "forecast",
                location: "Suwon",
                locationID: "1835553", //ID from http://bulk.openweathermap.org/sample/city.list.json.gz; unzip the gz file and find your city
                apiKey: "9030b6637989c1b9f6ecec80a4838d11"
            }
        },
        {
            module: "newsfeed",
            position: "bottom_bar",
            config: {
                feeds: [
                    {
                        title: "Health News",
                        url: "https://www.google.co.kr/alerts/feeds/17579249404884303951/5070263865270703960"
                    },
                    {
                        title: "Diet News",
                        url: "https://www.google.co.kr/alerts/feeds/17579249404884303951/17349925613568726563"
                    },
                ],
                showSourceTitle: true,
                showPublishDate: true,
                broadcastNewsFeeds: true,
                broadcastNewsUpdates: true
            }
        },
        {
            module: "MMM-CustomButtons",
            position: "lower_third",
            config: {
                // Custom buttons configuration (if any)
            }
        },
    ]
};

/*************** DO NOT EDIT THE LINE BELOW ***************/
if (typeof module !== "undefined") { module.exports = config; }

