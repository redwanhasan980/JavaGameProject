using System.Collections;


using UnityEngine;

using DG.Tweening;
public class SlingShotHandler : MonoBehaviour
{
    // Start is called before the first frame update

    [Header("Line Randerer")]
    [SerializeField] private LineRenderer LeftlineRenderer;
    [SerializeField] private LineRenderer RightlineRenderer;
    [Header("Transforms")]
    [SerializeField] private Transform LeftStartPoint;
    [SerializeField] private Transform RightStartPoint;
    [SerializeField] private Transform centerPosition;
    [SerializeField] private Transform idlePosition;
    [SerializeField] private Transform elasticTransform;
    [Header("Stats")]
    private Vector2 LinePosition;
    private bool clickedArea;
    [SerializeField] private float maxDistance = 3.5f;
    [SerializeField] private float maxAnimationTime = 1f;

    [SerializeField] private SlingshotArea slingshotArea;
    [Header("Bird")]
    [SerializeField] private AngieBird angieBirdPrefab;
    private AngieBird angieBird;
    private Vector2 direction;
    private Vector2 directionNormalized;
    [SerializeField] private float birdOffset = 2f;
    [SerializeField] private float shotForce = 5f;
    [SerializeField] private float timeOfBirdSpawn = 2f;
    [SerializeField] private float velocity = 1.2f;
    [SerializeField] private AnimationCurve elasticCurve;

    [Header("Sound")]
    [SerializeField] private AudioClip pulled;
    [SerializeField] private AudioClip[] Released;
    [SerializeField] private CameraManager cameraManager;
    
    private AudioSource _audioSource;

    private bool birdOnSlingShot;
    private void Awake() {
        LeftlineRenderer.enabled = false;
        RightlineRenderer.enabled = false;
        _audioSource = GetComponent<AudioSource>();
        
        SpawnBird();
    }
    void Start()
    {

    }
    // Update is called once per frame
    void Update()
    {
        play();
    }
    private void play()
    {
        if (InputManager.WasLeftButtonPressed && slingshotArea.IsInsideSlingShotArea())
        {
            clickedArea = true;
            if(birdOnSlingShot)
            {
                SoundManager.instance.PlayCLip(pulled, _audioSource);
                cameraManager.SwitchtoFOllowcam(angieBird.transform);
            }

        }
          
        if (InputManager.IsLeftMousePressed && clickedArea && birdOnSlingShot)
        {
            // Debug.Log("Left Button Pressed");
            DrawLines();
            positionAndRotationOFAngieBird();
        }
        if (InputManager.WasLeftButtonReleased && clickedArea && birdOnSlingShot)
        {
            if (GameManager.instance.HasEnoughShot())
            {
                clickedArea = false;
                angieBird.LaunchBird(direction, shotForce);
                SoundManager.instance.PlayRandomCLip(Released, _audioSource);
                GameManager.instance.UseShot();
                birdOnSlingShot = false;
                Animate();
                if (GameManager.instance.HasEnoughShot())
                    StartCoroutine(SpawnAngieBirdAfterTime());
            }
        }
    }
    #region SlingShot Method
    private void DrawLines()
    {

        Vector3 touchPosition = Camera.main.ScreenToWorldPoint(InputManager.MousePosition);
        LinePosition = centerPosition.position + Vector3.ClampMagnitude(touchPosition - centerPosition.position, maxDistance);
        SetLines(LinePosition);
        direction = (Vector2)centerPosition.position - LinePosition;
        directionNormalized = direction.normalized;
    }
    private void SetLines(Vector2 position)
    {
        if (!LeftlineRenderer.enabled && !RightlineRenderer.enabled)
        {
            LeftlineRenderer.enabled = true;
            RightlineRenderer.enabled = true;
        }
        LeftlineRenderer.SetPosition(0, position);
        LeftlineRenderer.SetPosition(1, LeftStartPoint.position);
        RightlineRenderer.SetPosition(0, position);
        RightlineRenderer.SetPosition(1, RightStartPoint.position);
    }
    #endregion
    #region ANgie Bird Method
    private void SpawnBird()
    {
        elasticTransform.DOComplete();
        Vector2 dir = (centerPosition.position - idlePosition.position).normalized;
        Vector2 spawnPosition = (Vector2)idlePosition.position + dir * birdOffset;
        SetLines(idlePosition.position);
        angieBird = Instantiate(angieBirdPrefab, spawnPosition, Quaternion.identity);
        angieBird.transform.right = dir;
        birdOnSlingShot = true;
    }
    private void positionAndRotationOFAngieBird()
    {
        angieBird.transform.position = LinePosition + directionNormalized * birdOffset;
        angieBird.transform.right = directionNormalized;

    }
    private IEnumerator SpawnAngieBirdAfterTime()
    {
        yield return new WaitForSeconds(timeOfBirdSpawn);
        //some after 2 second
        SpawnBird();
        cameraManager.SwitchtoIdlecam();
    }

    #endregion
    #region animate
    private void Animate()
    {
        elasticTransform.position = LeftlineRenderer.GetPosition(0);
        float dis = Vector2.Distance(elasticTransform.position, centerPosition.position);
        float time = dis / velocity;
        elasticTransform.DOMove(centerPosition.position, time).SetEase(elasticCurve);
        StartCoroutine(AnimateSlingShot(elasticTransform,time));

    }
    private IEnumerator AnimateSlingShot(Transform trans,float time)
    {
        float elaspedTime = 0f;
        while (elaspedTime < time &&elaspedTime<maxAnimationTime) 
        {
            elaspedTime += Time.deltaTime;
            SetLines(trans.position);
            yield return null;
        }
    } 
    #endregion
}
