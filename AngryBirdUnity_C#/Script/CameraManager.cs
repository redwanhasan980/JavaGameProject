using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Cinemachine;

public class CameraManager : MonoBehaviour
{
    [SerializeField] private CinemachineVirtualCamera _idleCam;
    [SerializeField] private CinemachineVirtualCamera _follow_cam;
    private void Awake()
    {
        SwitchtoIdlecam();
    }
    public void SwitchtoIdlecam()
    {
        _idleCam.enabled = true;
        _follow_cam.enabled = false;
    }
    public void SwitchtoFOllowcam(Transform followTrans)
    {
        _follow_cam.Follow = followTrans;
        _idleCam.enabled = false;
        _follow_cam.enabled = true;
       
    }
}
