package com.inventage.experiments.tentris.gameboard.sound;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import static com.google.common.collect.Sets.newConcurrentHashSet;
import static com.google.common.collect.Sets.newHashSet;

/**
 * Created by nw on 14.05.15.
 */
public class SoundManager {

  private Collection<Clip> clips = newConcurrentHashSet();

  private static final int sleepTimeMillis = 50;

  public void play(URL sound) {
    cleanUp();
    startPlaybackOf(sound);
  }

  private void cleanUp() {
    for (Clip c : newHashSet(clips)) {
      if (canBeRemoved(c)) {
        clips.remove(c);
        disposeOf(c);
      }
    }
  }

  private void disposeOf(Clip c) {
    if (c != null) {
      c.flush();
      c.close();
    }
  }

  private boolean canBeRemoved(Clip clip) {
    return !clip.isRunning();
  }

  private void startPlaybackOf(URL sound) {
    new Thread(new PlayerThread(clips, sound)).start();
  }

  private class PlayerThread implements Runnable {

    private Collection<Clip> clips;
    private URL sound;

    public PlayerThread(Collection<Clip> clips, URL sound) {
      this.clips = clips;
      this.sound = sound;
    }

    @Override
    public void run() {
     createClipAndPlaySound();
    }

    public void createClipAndPlaySound() {
      try {
        Clip clip = createClipWith(sound);
        clips.add(clip);
        clip.start();
      }
      catch (LineUnavailableException e) {
        e.printStackTrace();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
      catch (UnsupportedAudioFileException e) {
        e.printStackTrace();
      }
    }

    private Clip createClipWith(URL sound) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
      Clip clip = (Clip) AudioSystem.getLine(new Line.Info(Clip.class));
      clip.open(AudioSystem.getAudioInputStream(sound));
      clip.setFramePosition(0);  // Must always rewind!
      clip.loop(0);
      return clip;
    }
  }
}
