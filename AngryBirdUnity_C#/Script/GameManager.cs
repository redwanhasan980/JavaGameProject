using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;
using UnityEngine.SceneManagement;
using DG.Tweening;
using UnityEngine.UI;
public class GameManager : MonoBehaviour
{ public static GameManager instance;
    [SerializeField] private float DethCheck =3f;
    [SerializeField] private GameObject restartScreen;
    [SerializeField] SlingShotHandler shotHandler;
  public int MaxNumberOFShots = 3;
 public int usedNumberOfShot = 0;
    private IconHandler iconHandler;
    private List<piggy> _piggies = new List<piggy>();
    [SerializeField] private float timeOfRestart = 1f;
    [SerializeField] private Image nextLevelImg;

    private void Awake()
    {
        if (instance == null)
             { instance = this; }
        iconHandler = FindObjectOfType<IconHandler>();
        piggy[] piggies = FindObjectsOfType<piggy>();
        for (int i = 0; i < piggies.Length; i++)
        {
            _piggies.Add(piggies[i]);
        }
        nextLevelImg.enabled = false;
    }
    public void UseShot()
    {
        usedNumberOfShot++;
        iconHandler.UseShot(usedNumberOfShot);
        checkForLastShot(); 
    }
    public bool HasEnoughShot()
    {
         if(usedNumberOfShot <MaxNumberOFShots) return true;
         else return false;
    }
    public void checkForLastShot()
    {
        if(usedNumberOfShot == MaxNumberOFShots)
        {
            StartCoroutine(checkAfterWaitTIme());
        }
    }
    private IEnumerator checkAfterWaitTIme()
    {
         yield return new WaitForSeconds(DethCheck);
        if(_piggies.Count == 0)
        {
            winGame();
        }
        else
        {
            loseGame(); 
        }
   
    }
    public void removePiggy(piggy p1)

    {
        _piggies.Remove(p1);
        CheckForDead();
       
    }
    private void CheckForDead()
    {
        if (_piggies.Count == 0)
        {
            winGame();
        }
    }
    private void winGame()
    {
        Debug.Log("Win");
        restartScreen.SetActive(true);
        shotHandler.enabled = false;
        int current =SceneManager.GetActiveScene().buildIndex;
        int maxLevel=SceneManager.sceneCountInBuildSettings;
        if (current+1<maxLevel)
        {
            nextLevelImg.enabled = true;
        }
      
    }
    public void loseGame()
    {
        Debug.Log("lose");
        DOTween.Clear();
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
    public IEnumerator restartTime()
    {
        yield return new WaitForSeconds(timeOfRestart);
        //some after 2 second
        loseGame();
    }
    public void restartgame()
    {        
        StartCoroutine(restartTime());
    }
    public void nextLevel()
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex + 1);
    }
}
