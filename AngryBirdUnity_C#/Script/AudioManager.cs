using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AudioManager : MonoBehaviour
{
    [SerializeField] private float vol = 0.5f;
    private AudioSource audioSource;
    public AudioClip musicClip;
    public static AudioManager instance;
    private void Awake()
    {
        audioSource = GetComponent<AudioSource>();
        if (instance == null)
        {
            instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            Destroy(gameObject);
        }
       
        
    }

    private void Start()
    {
        audioSource.clip = musicClip;
        audioSource.volume = vol;
        audioSource.Play();   
    }
}
