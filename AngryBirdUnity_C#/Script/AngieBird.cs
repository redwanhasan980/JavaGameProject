using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AngieBird : MonoBehaviour
{
    private Rigidbody2D rb;
    private CircleCollider2D circleCollider;
    private bool hasBeenLauncheed;
    private bool shouldFace;
    [SerializeField] private AudioClip hitClip;
    private AudioSource hitSource;
    private void Awake()
    {
        rb = GetComponent<Rigidbody2D>();
        circleCollider = GetComponent<CircleCollider2D>();
        hitSource = GetComponent<AudioSource>();
       
    }
    private void Start()
    {
        rb.isKinematic = true;
        circleCollider.enabled = false;
    }
    private void FixedUpdate()
    {   
        if (hasBeenLauncheed && shouldFace)
        transform.right = rb.velocity;
    }
    public void LaunchBird(Vector2 direction,float force)
    {
        rb.isKinematic = false;
        circleCollider.enabled = true;
        rb.AddForce(direction*force,ForceMode2D.Impulse);
        hasBeenLauncheed = true;
        shouldFace = true;
    }
    private void OnCollisionEnter2D(Collision2D collision)
    {
        shouldFace = false;
        SoundManager.instance.PlayCLip(hitClip, hitSource);
    }


}
