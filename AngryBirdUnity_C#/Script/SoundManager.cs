using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SoundManager : MonoBehaviour
{
    public static SoundManager instance;
  
     private void Awake()
    {
         if (instance == null)  
            instance = this;
    }
    public void PlayCLip(AudioClip audioClip,AudioSource source)
    {
        source.clip = audioClip;
        source.Play();
    }
    public void PlayRandomCLip(AudioClip[] audioClip, AudioSource source)
    {
        int randomeIndex = Random.Range(0, audioClip.Length);
        source.clip=audioClip[randomeIndex];
        source.Play();
    }
        
}
