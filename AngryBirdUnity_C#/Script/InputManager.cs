using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.InputSystem;
public class InputManager : MonoBehaviour
{
    public static PlayerInput PlayerInput;
    private InputAction mousePosition;
    private InputAction mouse;
    public static Vector2 MousePosition;
    public static bool WasLeftButtonPressed;
    public static bool WasLeftButtonReleased;
    public static bool IsLeftMousePressed;

    private void Awake()
    {
        PlayerInput = GetComponent<PlayerInput>();
        mousePosition = PlayerInput.actions["MousePosition"];
        mouse = PlayerInput.actions["Mouse"];
    }
    private void Update()
    {
        MousePosition = mousePosition.ReadValue<Vector2>();
        WasLeftButtonPressed = mouse.WasPressedThisFrame();
        WasLeftButtonReleased = mouse.WasReleasedThisFrame();
        IsLeftMousePressed = mouse.IsPressed();

    }
}
