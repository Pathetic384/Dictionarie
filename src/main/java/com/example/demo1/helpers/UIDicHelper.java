package com.example.demo1.helpers;

import com.voicerss.tts.*;
import javafx.concurrent.Task;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

public class UIDicHelper extends Task<Clip> {

    private String text;
    private String language;

    public UIDicHelper(String text, String lang) {
        this.text = text;
        if(Objects.equals(lang, "en")) language = Languages.English_UnitedStates;
        if(Objects.equals(lang, "vi")) language = Languages.Vietnamese;
    }

    @Override
    protected Clip call() throws Exception {
        String PATH = "src/main/resources/audio.wav";
        VoiceProvider tts = new VoiceProvider("147452064f4d4c8e9217c6863b45990f");
        VoiceParameters params = new VoiceParameters(text, language);
        params.setCodec(AudioCodec.WAV);
        params.setFormat(AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(0);
        byte[] voice = tts.speech(params);
        FileOutputStream fos = new FileOutputStream(PATH);
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
        File audioFile = new File(PATH);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        return clip;
    }
}
