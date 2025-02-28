The purpose of this app is to turn a remote switch on or off, by sending it a text message. I generated most of the 
code using [DeepSeek]([url](https://www.deepseek.com/)) chat bot.

Here was my prompt:

```
Create an Android app that has two buttons “ON” and “OFF”. When a button is
pressed the code should send a preconfigured text message to a configured
phone number. It should then wait up to 30 seconds for a response from
the same phone number and display  it on the main screen.

The app should include a settings page which will allow the user to
configure the number to be called and the text strings to be send
when ON or OFF buttons are pressed.

Include a Settings button on main page to take you to the settings panel.
Settings button should be on the bottom of the screen.

Don’t forget to ask the user for appropriate permissions
- i.e. sending and receiving SMS.

Call the applications com.example.DSRemoteSwitch

Please use Kotlin for all the code.
```

The generated code was not entirely correct and I had to regenerate the code few times to get it right.
Here are some things that were missing and I had to add by hand:

* The user must be asked to give permission to allow the app send and received SMS messages.
* The code to receive a text message does not work. I have not debugged it, because I don't really need it.

