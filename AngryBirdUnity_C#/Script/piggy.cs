using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.InputSystem.XR.Haptics;

public class piggy : MonoBehaviour
{
    [SerializeField] private float maxHealth = 3f;
    [SerializeField] private float damageThreshold = 0.2f;
    [SerializeField] private GameObject DethParticle;
    [SerializeField] private AudioClip deathClip;

    private float currentHealth;
    private void Awake()
    {
        currentHealth=maxHealth;
    }
    public void DamageBuddie(float damage)
    {
        currentHealth-=damage;
        if (currentHealth <= 0f)
        {
            Die();
        }
    }
    private void Die()
    {    GameManager.instance.removePiggy(this);
        Instantiate(DethParticle,transform.position,Quaternion.identity);
        AudioSource.PlayClipAtPoint(deathClip,transform.position);
        Destroy(gameObject);
    }
    private void OnCollisionEnter2D(Collision2D collision)
    {
        float impactVelocity = collision.relativeVelocity.magnitude;
        if (impactVelocity > damageThreshold)
        {
            DamageBuddie(impactVelocity);
        }
    }
}
