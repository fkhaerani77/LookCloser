package com.cariperbedaan.audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class AudioManager {

    // Singleton
    private static AudioManager instance;

    private Clip bgmClip;
    private boolean musicOn = true;
    private boolean soundOn = true;
    private float bgmVolume = 0.7f;

    private AudioManager() {}

    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }

    // Play BGM dengan loop
    public void playBGM(String fileName) {
        if (!musicOn) return;

        try {
            // Stop BGM sebelumnya kalau ada
            stopBGM();

            URL url = getClass().getResource("/audio/" + fileName);
            if (url == null) {
                System.err.println("File audio tidak ditemukan: " + fileName);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            bgmClip = AudioSystem.getClip();
            bgmClip.open(ais);

            // Set volume
            setVolume(bgmClip, bgmVolume);

            // Loop terus menerus
            bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgmClip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Gagal play BGM: " + e.getMessage());
        }
    }

    // Stop BGM
    public void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop();
            bgmClip.close();
        }
    }

    // Play SFX sekali (untuk efek suara klik, benar, salah, dll)
    public void playSFX(String fileName) {
        if (!soundOn) return;

        try {
            URL url = getClass().getResource("/audio/" + fileName);
            if (url == null) {
                System.err.println("File SFX tidak ditemukan: " + fileName);
                return;
            }

            AudioInputStream ais = AudioSystem.getAudioInputStream(url);
            Clip sfxClip = AudioSystem.getClip();
            sfxClip.open(ais);
            setVolume(sfxClip, 1.0f);
            sfxClip.start();

            // Auto close setelah selesai
            sfxClip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    sfxClip.close();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Gagal play SFX: " + e.getMessage());
        }
    }

    // Set volume (0.0f - 1.0f)
    private void setVolume(Clip clip, float volume) {
        try {
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Konversi 0.0-1.0 ke desibel
            float dB = (float) (Math.log10(Math.max(volume, 0.0001f)) * 20);
            fc.setValue(Math.max(fc.getMinimum(), Math.min(dB, fc.getMaximum())));
        } catch (IllegalArgumentException e) {
            System.err.println("Gagal set volume: " + e.getMessage());
        }
    }

    // Toggle musik ON/OFF
    public void setMusicOn(boolean on) {
        this.musicOn = on;
        if (!on) {
            stopBGM();
        }
    }

    // Toggle suara ON/OFF
    public void setSoundOn(boolean on) {
        this.soundOn = on;
    }

    public boolean isMusicOn() { return musicOn; }
    public boolean isSoundOn() { return soundOn; }
}