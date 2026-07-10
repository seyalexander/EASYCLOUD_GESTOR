$baseDir = "c:\Users\nuevoadmin\Desktop\d\[03]_X_CYCLE\[01]_PRACTICE_X\Mult\EASYCLOUD_GESTOR\src\main\java\com\SeyaCloudGestion\GestionSistema\feacture"

$list1 = @()
$list2 = @()

$files = Get-ChildItem -Path $baseDir -Filter "*UseCase.java" -Recurse

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    if ($content -match "sqlServerTransactionManager") {
        $list1 += $file.Name
    } else {
        $matches = [regex]::Matches($content, "Registro[A-Za-z0-9]+UseCase")
        $otherRegistros = @()
        foreach ($m in $matches) {
            $matchName = $m.Value
            if ($matchName -ne $file.BaseName) {
                if ($otherRegistros -notcontains $matchName) {
                    $otherRegistros += $matchName
                }
            }
        }
        if ($otherRegistros.Count -gt 0) {
            $item = [PSCustomObject]@{
                UseCase = $file.Name
                LlamaA = ($otherRegistros -join ", ")
            }
            $list2 += $item
        }
    }
}

Write-Host "--- USAN sqlServerTransactionManager ---"
$list1 | Sort-Object | ForEach-Object { Write-Host $_ }

Write-Host "`n--- LES FALTA sqlServerTransactionManager Y LLAMAN A UN REGISTRO ---"
$list2 | Sort-Object UseCase | ForEach-Object { Write-Host "$($_.UseCase) (Llama a: $($_.LlamaA))" }
